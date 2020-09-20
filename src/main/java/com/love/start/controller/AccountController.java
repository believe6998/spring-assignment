package com.love.start.controller;

import com.love.start.dto.AccountDTO;
import com.love.start.entity.Account;
import com.love.start.rest.RESTResponse;
import com.love.start.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // create
    @PostMapping("/account")
    public ResponseEntity<Object> create(@RequestBody AccountDTO accountDTO) {
        accountDTO.setPassword(bCryptPasswordEncoder.encode(accountDTO.getPassword()));
        Account account = accountService.Save(accountDTO.ToAccountCreate());
        return new ResponseEntity<Object>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value()).setMessage("Ok").setData(account).build()
                , HttpStatus.CREATED);
    }

    // get all
    @GetMapping(value = "/account")
    public ResponseEntity<Object> getAll() {
        List<Account> accounts = accountService.FindAll();
        return new ResponseEntity<Object>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value()).setMessage("Ok").setData(accounts).build()
                , HttpStatus.OK);
    }

    // get one by id
    @GetMapping(value = "/account/{id}")
    public ResponseEntity<Object> getByID(@PathVariable(value = "id") Long id) {
        Account account = accountService.FindByID(id);
        if (account == null) {
            return new ResponseEntity<Object>(new RESTResponse.Success()
                    .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMessage("Error").build()
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Object>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value()).setMessage("Ok").setData(account).build()
                , HttpStatus.OK);
    }

    // edit
    @PutMapping(value = "/account/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody AccountDTO accountDTO) {
        accountDTO.setId(id);
        Account account = accountService.Update(accountDTO.ToAccount());
        if (account == null) {
            return new ResponseEntity<Object>(new RESTResponse.Success()
                    .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMessage("Error").build()
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Object>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value()).setMessage("Ok").setData(account).build()
                , HttpStatus.OK);
    }

    // delete
    @DeleteMapping(value = "/account/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        accountService.Delete(id);
        return new ResponseEntity<Object>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value()).setMessage("Ok").build()
                , HttpStatus.OK);
    }
}
