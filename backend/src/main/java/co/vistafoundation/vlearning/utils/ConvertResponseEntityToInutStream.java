package co.vistafoundation.vlearning.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ConvertResponseEntityToInutStream {

	InputStream targetStream;

	public ConvertResponseEntityToInutStream(byte[] arrayOfBytes) {
		this.targetStream = new ByteArrayInputStream(arrayOfBytes);
	}
}
