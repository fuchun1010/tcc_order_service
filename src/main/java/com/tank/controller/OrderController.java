package com.tank.controller;

import com.tank.common.ApiResult;
import com.tank.protocol.PreSubMoneyReq;
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

    /**
     * {
     *   "app": "order_service",
     *   "xid": "s0001",
     *   "money": 100,
     *   "accountId": 1
     * }
     */
    String accountReserveUrl = this.environment.getProperty("account_service.reserve.url");
    String accountCommitUrl = this.environment.getProperty("account_service.reserve.commit");
    String accountRollbackUrl = this.environment.getProperty("account_service.reserve.rollback");
    PreSubMoneyReq preSubMoneyReq = new PreSubMoneyReq();
    preSubMoneyReq.setAccountId(1).setApp("order_service").setMoney(100).setXid("s0001");

    ResponseEntity<ApiResult> result = this.restTemplate.postForEntity(accountReserveUrl, preSubMoneyReq, ApiResult.class);
    String error = result.getBody().getError();
    String preStatus = error == null ? "failure" : "success";

    return ResponseEntity.ok(apiResult);
  }

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private Environment environment;

}
