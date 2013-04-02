package eu.execom.core.service;

import java.io.IOException;
import java.io.InputStream;

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
