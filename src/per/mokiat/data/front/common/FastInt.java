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

package per.mokiat.data.front.common;

/**
 * Internal implementation of the {@link IFastInt}
 * interface.
 * 
 * @author Momchil Atanasov
 * 
 */
public class FastInt implements IFastInt {
	
	private int value;
	
	public FastInt() {
		super();
	}
	
	public void set(int value) {
		this.value = value;
	}

	@Override
	public int get() {
		return value;
	}

}
