package core;

import lombok.Getter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

public class PropertyLoader {

    private static final Logger LOGGER = LogManager.getLogger("Log");

    private PropertyLoader() {
    }


    private static String retornarValorArquivoConfiguracao(final String propriedade) {
        Properties properties;

        try {
            properties = new Properties();

            try (InputStream propFileInpStream = PropertyLoader.class.getClassLoader()
                    .getResourceAsStream("config.properties")) {
                properties.load(propFileInpStream);
            }
   return properties.getProperty(propriedade);

        } catch (IOException | NullPointerException e) {
            LOGGER.error(MessageFormat.format("Propriedade {0} não foi encontrada nos arquivos de configuração", propriedade), e);
        }
        return null;
    }

    @Getter
    public enum Service {

        BASEURI {
            @Override
            public String getValue() {
                return retornarValorArquivoConfiguracao("service.baseuri");
            }
        };

        public abstract String getValue();

    }


}