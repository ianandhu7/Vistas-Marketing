/**
 * 
 */
package co.vistafoundation.vlearning.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author NaveenKumar
 *
 */

@Component
public class PreCommitInitializers {

	@Value("${app.dns.url}")
	private String appDNS;

	private static final Logger logger = LoggerFactory.getLogger(PreCommitInitializers.class);

	private static String previosVersion = "1.0";

	// current version of the script
	private static String currectVersion = "1.0";

	String hookScript = "#!/bin/bash\n" + "# intalizing process\n" + "echo\n"
			+ "echo \"invoking git pre-commit hook\"\n" + "echo    \n" + "echo \"Building war file started.\"\n"
			+ "echo\n" + "sleep 2\n" + "echo \"Skipping Test Cases.\"\n" + "sleep 2\n" + "echo\n" + "#build process\n"
			+ "mvn clean package -Dmaven.test.skip\n" + "\n" + "#build verification process\n"
			+ "if [ \"$?\" -ne 0 ]; then\n" + "    sleep 2\n" + "    echo\n" + "    echo\n"
			+ "    echo \"Unable to build the  war file!\"\n" + "    sleep 2\n" + "    echo\n"
			+ "    echo \"Please review the code base.\"\n" + "    sleep 1\n" + "    echo\n"
			+ "    echo \"Code cannot be commited!\"\n" + "    echo\n" + "    exit 1\n" + "else \n" + "echo \n"
			+ "sleep 1\n" + "echo \"war file build succesfull.\"\n" + "echo \n" + "sleep 1\n"
			+ "echo \"intalizing git commit\"\n" + "echo\n" + "echo\n" + "exit 0\n" + "fi\n" + " #script-version-"
			+ currectVersion;

	@EventListener(ApplicationReadyEvent.class)
	public void loadData() {

		try {

			// event is applicable only during in the local server
			if (appDNS.equalsIgnoreCase("localhost:9090")) {
				// Specify the pre-commit file path
				String hookFileName = ".git/hooks/pre-commit";
				Path hookFilePath = Path.of(hookFileName);

				String hookFileVersion = ".git/hooks/pre-commit-" + currectVersion;
				Path versionFilePath = Path.of(hookFileVersion);

				// check version file existence
				if (Files.exists(versionFilePath)) {

					// checking whether hook file exists or not
					if (Files.exists(hookFilePath)) {

						logger.info("Pre-commit hook file check completed," + " File Already Exists.");

					} else {
						// Create the pre-commit file
						this.fileWriter(hookFilePath);
						logger.info("Pre-commit hook file created successfully.");
					}

				} else {

					String previousHookFileVersion = ".git/hooks/pre-commit-" + previosVersion;
					Path previosVersionFilePath = Path.of(previousHookFileVersion);

					if (Files.exists(previosVersionFilePath)) {
						Files.delete(previosVersionFilePath);
					}
					// create version File
					this.fileWriter(versionFilePath);
					logger.info("Pre-commit version file created successfully.");

					if (Files.exists(hookFilePath)) {
						Files.delete(hookFilePath);
						this.fileWriter(hookFilePath);
						logger.info("Pre-commit hook file updated successfully.");

					} else {
						// Create the pre-commit file
						this.fileWriter(hookFilePath);
						logger.info("Pre-commit hook file created successfully.");
					}

				}

			}

		} catch (IOException e) {
			logger.error("Error occurred while creating or installing the pre-commit hook file.");
			logger.error(e.getLocalizedMessage());
		}

	}

	private void fileWriter(Path pathFile) throws IOException {

		BufferedWriter writer = null;

		try {

			writer = new BufferedWriter(new FileWriter(pathFile.toFile()));

			writer.write(hookScript);
			// Set executable permissions for the pre-commit file
			Set<PosixFilePermission> permissions = new HashSet<>();
			permissions.add(PosixFilePermission.OWNER_EXECUTE);
			permissions.add(PosixFilePermission.GROUP_EXECUTE);
			permissions.add(PosixFilePermission.OWNER_READ);
			permissions.add(PosixFilePermission.GROUP_READ);

			if (Files.getFileStore(pathFile).supportsFileAttributeView(PosixFileAttributeView.class)) {
				Files.setPosixFilePermissions(pathFile, permissions);
			}
			// Set executable permission for Windows systems
			else {
				if(pathFile.toFile().setExecutable(Boolean.TRUE)) {
					System.out.println("file created successfully");
				}
		
			}

		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}

		finally {
			if (writer != null)	
			{
				 writer.close();
			}		     
		}

	}

}
