package app.gpx_animator.core.renderer.plugins;

import app.gpx_animator.core.Constants;
import app.gpx_animator.core.configuration.Configuration;
import app.gpx_animator.core.data.Position;
import app.gpx_animator.core.renderer.RendererPlugin;
import app.gpx_animator.core.renderer.RenderingContext;
import app.gpx_animator.core.renderer.TextRenderer;
import app.gpx_animator.core.util.MapUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

@SuppressWarnings("unused") // Plugins are loaded using reflection
public final class AttributionPlugin extends TextRenderer implements RendererPlugin {

    private final transient String attribution;
    private final transient Position position;
    private final transient int margin;

    public AttributionPlugin(@NotNull final Configuration configuration) {
        super(configuration.getFont());

        final var mapTemplate = MapUtil.getMapTemplate(configuration.getTmsUrlTemplate());
        final var mapAttribution = mapTemplate == null ? "" : mapTemplate.getAttributionText();

        attribution = configuration.getAttribution()
                .replace("%APPNAME_VERSION%", Constants.APPNAME_VERSION)
                .replace("%MAP_ATTRIBUTION%", mapAttribution);
        position = configuration.getAttributionPosition();
        margin = configuration.getAttributionMargin();
    }

    @Override
    public int getOrder() {
        return 1_000;
    }

    @Override
    public void renderBackground(@NotNull final BufferedImage image, @NotNull final RenderingContext context) { }

    @Override
    public void renderFrame(final int frame, @NotNull final BufferedImage image, @NotNull final RenderingContext context) {
        renderText(attribution, position, margin, image);
    }

}
