package com.tank.protocol;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

public class CoordinatorReq {

  private  <T> RouterBean<T> createRouter(@NonNull final String url, @NonNull final T payLoad) {
    RouterBean<T> routerBean = new RouterBean<>();
    routerBean.setPayLoad(payLoad);
    routerBean.setUrl(url);
    return routerBean;
  }

  public <T> void addRouter(@NonNull final String url, @NonNull final T payLoad) {
    RouterBean<T> router = this.createRouter(url, payLoad);
    this.commits.add(router);
  }

  public <T> void addRollBack(@NonNull final String url, @NonNull final T payLoad) {
    RouterBean<T> router = this.createRouter(url, payLoad);
    this.rollbacks.add(router);
  }

  @Setter
  @Getter
  public static class RouterBean<T> {
    private String url;
    private T payLoad;
  }

  private String status;
  @Getter
  private List<RouterBean> commits = Lists.newArrayListWithCapacity(1 << 7);
  @Getter
  private List<RouterBean> rollbacks = Lists.newArrayListWithCapacity(1 << 7);


}
