package com.example.login_app.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.login_app.model.Achievement;
import com.example.login_app.repository.AchievementRepository;

@Component
public class AchievementDataLoader implements CommandLineRunner {

    private final AchievementRepository repo;

    public AchievementDataLoader(AchievementRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {
            repo.save(new Achievement("WEIGHT_LOSS_BRONZE","Perdido 2kg","Pierde los primeros 2kg",2.0, Achievement.Level.BRONZE));
            repo.save(new Achievement("WEIGHT_LOSS_SILVER","Perdido 5kg","Pierde 5kg",5.0, Achievement.Level.SILVER));
            repo.save(new Achievement("WEIGHT_LOSS_GOLD","Perdido 10kg","Pierde 10kg",10.0, Achievement.Level.GOLD));
        }
    }
}
