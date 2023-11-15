package com.practice.borrowing.service;

import com.practice.borrowing.dto.BorrowingDTO;
import com.practice.borrowing.entity.Borrowing;
import com.practice.borrowing.exceptions.BorrowingNotFoundException;
import com.practice.borrowing.feign.Book;
import com.practice.borrowing.feign.BookFeign;
import com.practice.borrowing.feign.UserFeign;
import com.practice.borrowing.repository.BorrowingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository repository;

    @Autowired
    private BookFeign bookFeign;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private SequenceGeneratorService sequence;




    public List<Borrowing> getAllBorrowings(){
        return repository.findAll();
    }

    public Optional<Borrowing> getOne(Integer id){
        return Optional.of(repository.findById(id)).orElseThrow(
                ()->new BorrowingNotFoundException(id));
    }

    public Borrowing insert(BorrowingDTO borrowingDTO){
        Borrowing borrowing = new Borrowing();
        borrowing.setId(sequence.getSequenceNumber(Borrowing.SEQUENCE_NAME));
        borrowing.setUser(userFeign.getOne(borrowingDTO.getUser()));
        borrowing.setBooks(bookFeign.getSeveral(borrowingDTO.getBooks()));
        borrowing.setBeginingDate(LocalDate.now());
        borrowing.setEndDate(borrowing.getBeginingDate().plusDays(30));
        borrowing.setTotalPrice(calculateTotalPrice(borrowing));
        return repository.save(borrowing);
    }

    public Double calculateTotalPrice(Borrowing borrowing){
        return borrowing.getBooks().stream().mapToDouble(
                Book::getPrice).sum();
    }

    public Optional<Borrowing> updateBorrowing(BorrowingDTO borrowingDTO, Integer id){
        return Optional.ofNullable(repository.findById(id)
                .map(borrowing -> {
                    borrowing.setId(borrowing.getId());
                    borrowing.setUser(userFeign.getOne(borrowingDTO.getUser()));
                    borrowing.setBooks(bookFeign.getSeveral(borrowingDTO.getBooks()));
                    borrowing.setBeginingDate(LocalDate.now());
                    borrowing.setEndDate(borrowing.getBeginingDate().plusDays(30));
                    borrowing.setTotalPrice(calculateTotalPrice(borrowing));
                    return repository.save(borrowing);
                }).orElseThrow(() -> new BorrowingNotFoundException(id)));
    }

    public Optional<Borrowing> updateByField(Integer id, BorrowingDTO updatedBorrowingDTO) {
        Optional<Borrowing> existingBorrowing = repository.findById(id);
        if (existingBorrowing.isPresent()) {
            Borrowing existingBorrowingEntity = existingBorrowing.get();
            BeanUtils.copyProperties(updatedBorrowingDTO, existingBorrowingEntity,
                    getNullPropertyNames(updatedBorrowingDTO));
            return Optional.of(repository.save(existingBorrowingEntity));
        } else {
            return Optional.empty();
        }
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public Boolean deleteBorrowing(Integer id){
        Optional<Borrowing> existBorrowing = repository.findById(id);

        if(existBorrowing.isPresent()){
            repository.delete(existBorrowing.get());
            return true;
        }
        return false;
    }






}
