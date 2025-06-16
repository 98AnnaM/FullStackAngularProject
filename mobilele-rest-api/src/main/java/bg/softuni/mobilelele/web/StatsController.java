package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.StatsView;
import bg.softuni.mobilelele.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatsView> statistics() {
        return ResponseEntity.ok(statsService.getStats());
    }
}
