package eu.execom.core.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import eu.execom.core.dto.AbstractDto;

/**
 * Utility class used to initialize values of email components.
 * 
 * @author Dusko Vesin
 */
@Service
class FileServiceImpl {

    private static final int BUFFER_SIZE = 1024;
    private static final String UNABLE_TO_PROCESS_FILE = "Unable to process file ";
    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);
    private final ImageByteArrayDto emailHeader;

    /**
     * {@link FileServiceImpl} constructor.
     * 
     * @throws IOException
     *             is thrown is some of resource files are not available
     * 
     */
    public FileServiceImpl() throws IOException {
        emailHeader = createImageByteArrayDto(loadByteArrayFromClassPath("/META-INF/mail/header.jpg"), "jpg");

    }

    /**
     * Converts {@link InputStream} to {@link String}.
     * 
     * @param stream
     *            for converting
     * @return String value of the stream
     * @throws IOException
     *             in cases when conversion is not possible
     */
    public String convertStreamToString(final InputStream stream) throws IOException {
        /*
         * To convert the InputStream to String we use the Reader.read(char[] buffer) method. We iterate until the
         * Reader return -1 which means there's no more data to read. We use the StringWriter class to produce the
         * string.
         */
        if (stream != null) {
            final Writer writer = new StringWriter();

            final char[] buffer = new char[BUFFER_SIZE];
            try {
                final Reader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                stream.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    private ImageByteArrayDto createImageByteArrayDto(final byte[] image, final String imageType) {
        final ImageByteArrayDto imageByteArrayDto = new ImageByteArrayDto();
        imageByteArrayDto.setImage(image);
        imageByteArrayDto.setImageType(imageType);
        return imageByteArrayDto;
    }

    private byte[] loadByteArrayFromClassPath(final String resourcePath) throws IOException {
        final ClassPathResource classPathResource = new ClassPathResource(resourcePath);
        InputStream inputStream = classPathResource.getInputStream();
        return IOUtils.toByteArray(inputStream);
    }

    public static byte[] convertFileToByteArray(final File input) {
        try {
            return FileUtils.readFileToByteArray(input);
        } catch (final IOException e) {
            LOG.error("Error converting file " + input.getName() + " to byte array.");
            throw new RuntimeException();
        }
    }

    public ImageByteArrayDto getEmailHeader() {
        return emailHeader;
    }

    public class ImageByteArrayDto extends AbstractDto {

        private byte[] image;
        public static final String IMAGE = "image";

        public void setImage(final byte[] image) {
            this.image = image;
        }

        public byte[] getImage() {
            return image;
        }

        private String imageType;
        public static final String IMAGETYPE = "imageType";

        public void setImageType(final String imageType) {
            this.imageType = imageType;
        }

        public String getImageType() {
            return imageType;
        }

    }

}
