package com.globant.vtxintro;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class FeatureVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> fut) throws Exception {
    Future<Void> future = Future.future();
    super.start(future);

    future.setHandler(handler -> {
      vertx.eventBus().consumer("format_whisky_name", this::onMessage);
      fut.complete();
    });
  }

  public void onMessage(Message<JsonObject> message) {
    JsonObject request = message.body();
    String newMessage = request.getString("name") + "_trail";
    JsonObject jsonResponse = new JsonObject();
    jsonResponse.put("result", newMessage);
    message.reply(jsonResponse);
  }
}
