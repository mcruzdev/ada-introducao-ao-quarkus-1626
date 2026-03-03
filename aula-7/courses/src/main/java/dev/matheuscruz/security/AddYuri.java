package dev.matheuscruz.security;

import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
@Startup
public class AddYuri {

    public void addYuri(@Observes StartupEvent event) {
        QuarkusTransaction.requiringNew().run(() -> User.add("yuri", "yuri123", "admin"));
    }
}
