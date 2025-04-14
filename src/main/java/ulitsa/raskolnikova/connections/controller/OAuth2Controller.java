package ulitsa.raskolnikova.connections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import ulitsa.raskolnikova.connections.entity.connection.ExternalConnection;
import ulitsa.raskolnikova.connections.service.ExternalConnectionService;
import ulitsa.raskolnikova.connections.util.RedirectUrlManager;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/connections/oauth2")
public class OAuth2Controller {
    private final ExternalConnectionService externalConnectionService;
    private final RedirectUrlManager redirectUrlManager;

    @GetMapping("/code")
    public RedirectView generateAccessTokenAndRedirect(
            @RequestParam String code,
            @RequestParam(name = "state") UUID connectionId
    ) throws IOException, InterruptedException {
        ExternalConnection connection = externalConnectionService.createOAuth2ExternalConnection(connectionId, code);
        return new RedirectView(redirectUrlManager.getRedirectUrl(connection.getUserId())
                + "?connection_id=" + connection.getId());
    }
}
