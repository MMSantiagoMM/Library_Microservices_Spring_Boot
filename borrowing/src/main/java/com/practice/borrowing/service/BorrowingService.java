package com.practice.borrowing.service;

import com.practice.borrowing.dto.BorrowingDTO;
import com.practice.borrowing.entity.Borrowing;
import com.practice.borrowing.exceptions.BorrowingNotFoundException;
import com.practice.borrowing.feign.Book;
import com.practice.borrowing.feign.BookFeign;
import com.practice.borrowing.feign.UserFeign;
import com.practice.borrowing.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Optional<Borrowing> updateByField(Integer id, Map<String,Object> fields){
        Optional<Borrowing> existingBorrowing = repository.findById(id);
        if(existingBorrowing.isPresent()){
            fields.forEach((key,value)->{
                Field field = ReflectionUtils.findField(Borrowing.class,key);
                field.setAccessible(true);
                ReflectionUtils.setField(field,existingBorrowing.get(),value);
            });
            return Optional.of(repository.save(existingBorrowing.get()));
        }
        return Optional.empty();
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
