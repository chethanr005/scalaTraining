package com.chethan.assignment6.employee;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.headers.BasicHttpCredentials;
import akka.http.scaladsl.model.HttpMethods;

import java.util.ArrayList;
import java.util.concurrent.CompletionStage;

/**
 * Created by Chethan on Mar 25, 2022.
 */

public class EmployeeClientApi {
    public static void main(String[] args) {

        ActorSystem<Void> system = ActorSystem.create(Behaviors.empty(), "routes");
        final Http          http   = Http.get(system);
        HttpServer                           app     = new HttpServer(new EmployeeDatabase());
        final CompletionStage<ServerBinding> binding = http.newServerAt("localhost", 8008).bind(app.createRoute());
        System.out.println("Server online at http://localhost:8008/");

        HttpRequest request = HttpRequest.create().withMethod(HttpMethods.GET()).withUri("http://localhost:8008/employee")
                                         .addCredentials(BasicHttpCredentials.createBasicHttpCredentials("cooper", "11"));

        CompletionStage<HttpResponse> responseFuture = Http.get(system).singleRequest(request);

        responseFuture.whenComplete((response, throwable) -> {
            CompletionStage<ArrayList> student = Jackson.unmarshaller(ArrayList.class).unmarshal(response.entity(), system);
            student.whenComplete((studentList, throwable2) -> {
                System.out.println(studentList);
            });
        });
    }
}
