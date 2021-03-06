/*
 * Copyright (C) mokiat.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package per.mokiat.data.front.scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import per.mokiat.data.front.error.WFException;

/**
 * Default implementation of the {@link IOBJScanner}
 * interface.
 * 
 * @author Momchil Atanasov
 * 
 */
public class OBJScanner implements IOBJScanner {
	
	/**
	 * Creates a new instance of the {@link OBJScanner} class.
	 */
	public OBJScanner() {
		super();
	}

	@Override
	public void scan(InputStream in, IOBJScannerHandler handler) throws WFException, IOException {
        final Reader reader = new InputStreamReader(in);
        scan(new BufferedReader(reader), handler);
	}
	
	@Override
	public void scan(BufferedReader reader, IOBJScannerHandler handler) throws WFException, IOException {
		final OBJScanRunner runner = new OBJScanRunner(handler);
		runner.run(reader);
	}

}
