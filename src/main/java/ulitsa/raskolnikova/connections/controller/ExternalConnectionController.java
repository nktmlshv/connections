package ulitsa.raskolnikova.connections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulitsa.raskolnikova.connections.dto.connection.ExternalConnectionDto;
import ulitsa.raskolnikova.connections.dto.connection.util.ExternalConnectionResponse;
import ulitsa.raskolnikova.connections.dto.connection.util.OAuth2Url;
import ulitsa.raskolnikova.connections.service.ExternalConnectionService;
import ulitsa.raskolnikova.connections.util.RedirectUrlManager;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/connections")
public class ExternalConnectionController {
    private final ExternalConnectionService externalConnectionService;
    private final RedirectUrlManager redirectUrlManager;

    @PostMapping
    public ResponseEntity<ExternalConnectionResponse> createExternalConnection(
            @RequestParam(name = "redirect_url") String redirectUrl,
            @RequestBody ExternalConnectionDto connection) {
        ExternalConnectionResponse response = externalConnectionService.createExternalConnection(connection);
        if (response instanceof OAuth2Url) {
            redirectUrlManager.saveRedirectUrl(connection.getUserId(), redirectUrl);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{connectionId}")
    public ResponseEntity<ExternalConnectionDto> getExternalConnection(@PathVariable UUID connectionId)
            throws IOException, InterruptedException {
        return ResponseEntity.ok(externalConnectionService.getExternalConnection(connectionId));
    }

    @PutMapping("/{connectionId}")
    public ResponseEntity<ExternalConnectionResponse> updateExternalConnection(
            @PathVariable UUID connectionId, @RequestBody ExternalConnectionDto connection) {
        connection.setId(connectionId);
        return ResponseEntity.ok(externalConnectionService.updateExternalConnection(connection));
    }

    @DeleteMapping("/{connectionId}")
    public ResponseEntity<Void> deleteExternalConnection(@PathVariable UUID connectionId) {
        externalConnectionService.deleteExternalConnection(connectionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExternalConnectionDto>> getUserExternalConnections(@PathVariable UUID userId) {
        return ResponseEntity.ok(externalConnectionService.getUserExternalConnections(userId));
    }
}
