package com.tank.controller;

import com.tank.common.ApiResult;
import com.tank.protocol.CoordinatorReq;
import com.tank.protocol.PreSubMoneyReq;
import com.tank.protocol.RollbackAccountReq;
import com.tank.protocol.SubAccountReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/v1")
public class OrderController {

  @PostMapping("/preSub")
  public ResponseEntity<ApiResult<String>> preSub() {
    ApiResult<String> apiResult = new ApiResult<>();
    //TODO send reserve operation to account_service

    String accountReserveUrl = this.environment.getProperty("account_service.reserve.url");
    String accountCommitUrl = this.environment.getProperty("account_service.reserve.commit");
    String accountRollbackUrl = this.environment.getProperty("account_service.reserve.rollback");

    String coordinatorUrl = this.environment.getProperty("transaction_coordinator.server.url");

    PreSubMoneyReq preSubMoneyReq = new PreSubMoneyReq();
    preSubMoneyReq.setAccountId(1).setApp("order_service").setMoney(100).setXid("s0001");

    ResponseEntity<ApiResult> result = this.restTemplate.postForEntity(accountReserveUrl, preSubMoneyReq, ApiResult.class);
    String error = result.getBody().getError();
    String preStatus = error == null ? "preFailure" : "success";

    CoordinatorReq coordinatorReq = new CoordinatorReq();
    coordinatorReq.setStatus(preStatus);

    SubAccountReq subAccountReq = new SubAccountReq();
    subAccountReq.setXid(preSubMoneyReq.getXid());
    coordinatorReq.addCommit(accountCommitUrl, subAccountReq);

    RollbackAccountReq rollbackAccountReq = new RollbackAccountReq();
    rollbackAccountReq.setXid(preSubMoneyReq.getXid());
    coordinatorReq.addRollBack(accountRollbackUrl, rollbackAccountReq);

    //提交到到事务协调器
    this.restTemplate.postForEntity(coordinatorUrl, coordinatorReq, ApiResult.class);


    return ResponseEntity.ok(apiResult);
  }

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private Environment environment;

}
