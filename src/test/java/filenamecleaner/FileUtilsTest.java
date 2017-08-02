package filenamecleaner;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.home.utils.FileUtils;

public class FileUtilsTest {

	private static final Logger LOG = LoggerFactory.getLogger(FileUtilsTest.class);
	
	@Test
	public void testGetFiles() {
		try {
		URL url = this.getClass().getResource("/");
		List<File> fileList = FileUtils.getFilesFromDir(new File(url.getFile()), false);
		
		LOG.info("Found :" + fileList.size() + " Number of Files in Directory.");
		} catch (final Exception e) {
			LOG.error("Exception on testGetFiles", e);
			fail("testGetFiles");
		}
	}
}
