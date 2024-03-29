/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.googlecode.rich2012cafe.shared;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

/**
 * GENERATED CLASS
 * 
 * A proxy object containing a message destined for a particular
 * recipient, identified by email address.
 */
@ProxyForName(value = "com.googlecode.rich2012cafe.server.utils.Message",
    locator = "com.googlecode.rich2012cafe.server.utils.MessageLocator")
public interface MessageProxy extends ValueProxy {
  String getMessage();
  String getRecipient();
  void setRecipient(String recipient);
  void setMessage(String message);
}
