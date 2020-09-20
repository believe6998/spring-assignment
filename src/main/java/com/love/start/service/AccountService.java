package com.love.start.service;

import com.love.start.entity.Account;
import com.love.start.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account Save(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> FindAll() {
        return accountRepository.findAll();
    }

    public Account FindByID(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        return accountOptional.orElse(null);
    }

    public Account FindByUsername(String username) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        return accountOptional.orElse(null);
    }

    public Account Update(Account account) {
        Account accTemp = FindByID(account.getId());
        if (accTemp == null) {
            return null;
        }

        Long timeNow = (long) Calendar.getInstance().get(Calendar.MILLISECOND);

        accTemp.setEmail(account.getEmail());
        accTemp.setPassword(account.getPassword());
        accTemp.setUpdateAt(timeNow);
        return accountRepository.save(accTemp);
    }

    public void Delete(Long id) {
        Account account = FindByID(id);
        accountRepository.delete(account);
    }
}
