package com.chethan.assignment6.student;

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

public class StudentClientApi {
    public static void main(String[] args) throws Exception {
        ActorSystem<Void>                    system  = ActorSystem.create(Behaviors.empty(), "routes");
        final Http                           http    = Http.get(system);
        HttpServer                           app     = new HttpServer(new StudentDatabase());
        final CompletionStage<ServerBinding> binding = http.newServerAt("localhost", 8888).bind(app.createRoute());
        System.out.println("Server online at http://localhost:8888/");

        HttpRequest request = HttpRequest.create().withMethod(HttpMethods.GET()).withUri("http://localhost:8888/student")
                                         .addCredentials(BasicHttpCredentials.createBasicHttpCredentials("rose", "r001"));

        CompletionStage<HttpResponse> responseFuture = Http.get(system).singleRequest(request);

        responseFuture.whenComplete((response, throwable) -> {
            CompletionStage<ArrayList> student = Jackson.unmarshaller(ArrayList.class).unmarshal(response.entity(), system);
            student.whenComplete((studentList, throwable2) -> {
                System.out.println(studentList);
            });
        });

        HttpRequest request2 = HttpRequest.create().withMethod(HttpMethods.GET()).withUri("http://localhost:8888/student/r005")
                                         .addCredentials(BasicHttpCredentials.createBasicHttpCredentials("rose", "r001"));

        CompletionStage<HttpResponse> responseFuture2 = Http.get(system).singleRequest(request2);

        responseFuture2.whenComplete((response, throwable) -> {
            CompletionStage<Student> student = Jackson.unmarshaller(Student.class).unmarshal(response.entity(), system);
            student.whenComplete((studentList, throwable2) -> {
                System.out.println(studentList);
            });
        });

    }
}
