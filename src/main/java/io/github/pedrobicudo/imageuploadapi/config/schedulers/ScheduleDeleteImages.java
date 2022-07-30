package io.github.pedrobicudo.imageuploadapi.config.schedulers;

import io.github.pedrobicudo.imageuploadapi.model.domain.services.interfaces.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
public class ScheduleDeleteImages {

    private static Date maxDate = new Date();
    private static final int ONE_HOUR = 3600000;

    @Autowired
    private IImageService imageService;

    @Scheduled(fixedDelay = ONE_HOUR)
    public void deleteImagesBeforeMaxDate() {
        imageService.deleteAllImagesBefore(maxDate);
        maxDate = new Date();
    }
}
