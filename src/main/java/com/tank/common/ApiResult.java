package com.tank.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResult<T> {

  private int status;
  private T data;
  private String error;
}
