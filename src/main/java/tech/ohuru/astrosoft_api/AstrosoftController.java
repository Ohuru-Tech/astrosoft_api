package tech.ohuru.astrosoft_api;

import com.innovativeastrosolutions.astrosoftcore.beans.AstroData;
import com.innovativeastrosolutions.astrosoftcore.core.Horoscope;
import com.innovativeastrosolutions.astrosoftcore.core.Panchang;
import com.innovativeastrosolutions.astrosoftcore.results.HoroscopeResult;
import com.innovativeastrosolutions.astrosoftcore.results.PanchangResult;
import com.innovativeastrosolutions.astrosoftcore.util.FeatureSet;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class AstrosoftController {
    Logger logger = LoggerFactory.getLogger(AstrosoftController.class);

    @GetMapping("/ping")
    String healthCheck() {

        logger.info("Health Check!!");
        return "Alive !!";
    }

    @PostMapping("/horoscopeTest/{featureSetString}")
    HoroscopeResult horoscopeTest(AstroData astroData, @PathVariable String featureSetString) {

        logger.debug("Got request for horoscope for feature set : " + featureSetString);

        Horoscope horoscope = new Horoscope(astroData);
        FeatureSet featureSet = FeatureSet.valueOf(featureSetString);
        horoscope.calculate(featureSet);

        return horoscope.getResult();
    }

    @PostMapping("/horoscope")
    HoroscopeResult horoscope(AstroData astroData) {

        logger.info("Got request for horoscope ");

        Horoscope horoscope = new Horoscope(astroData);
        try {
            horoscope.calculate(FeatureSet.HoroscopeBasic);
        } catch (Throwable throwable) {
            throw new AstroResultException(throwable.getMessage());
        }
        return horoscope.getResult();
    }

    @PostMapping("/panchang")
    PanchangResult panchang(AstroData astroData) {

        logger.debug("Got request for panchang");

        Panchang panchang = new Panchang(astroData);
        try {
            panchang.calculate(FeatureSet.PanchangBasic);
        } catch (Throwable throwable) {
            throw new AstroResultException(throwable.getMessage());
        }

        return panchang.getResult();
    }
}
