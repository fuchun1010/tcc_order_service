package com.tank.protocol;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PreSubMoneyReq {
  /**
   * app : order_service
   * xid : s0001
   * money : 100
   * accountId : 1
   */

  private String app;
  private String xid;
  private int money;
  private int accountId;


}
