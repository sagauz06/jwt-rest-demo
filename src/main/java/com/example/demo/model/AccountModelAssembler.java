package com.example.demo.model;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;

import com.example.demo.controllers.AccountController;
import com.example.demo.domain.Account;

@Component
public class AccountModelAssembler extends RepresentationModelAssemblerSupport<Account, AccountModel> {

	public AccountModelAssembler() {
		super(AccountController.class, AccountModel.class);
	}

	@Override
	public AccountModel toModel(Account entity) {
//		方法1
//		AccountModel accountModel = createModelWithId(entity.getId(), entity);
//		方法2
		AccountModel accountModel = instantiateModel(entity);
//
		accountModel.add(linkTo(methodOn(AccountController.class).findOne(entity.getId())).withSelfRel());
		accountModel.add(linkTo(methodOn(AccountController.class).findAll()).withRel("accounts"));
//
//		accountModel.setContent(entity.getContent());
//		accountModel.setPerson(entity.getPerson());

		return accountModel;
	}

	@Override
	public CollectionModel<AccountModel> toCollectionModel(Iterable<? extends Account> entities) {
		CollectionModel<AccountModel> actorModels = super.toCollectionModel(entities);
		actorModels.add(linkTo(methodOn(AccountController.class).findAll()).withSelfRel());
		return actorModels;
	}

	@Override
	protected AccountModel instantiateModel(Account entity) {
		return new AccountModel(entity);
	}

}
