/*
 * Copyright 2024 Safe Health Systems, Inc.
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
package cdx.opencdx.communications.service;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import java.util.Map;

/**
 * An interface for processing a template of HTML with variable substitution
 * to generate HTML with fully substitute
 * @implSpec Implement this interface using your SMS system.
 * @implNote Current implementation does not send the SMS, just outputs the email to logging
 * for verification it was called, for demonstration purposes only.
 */
public interface OpenCDXHTMLProcessor {
    /**
     * Method to perform the HTML processing
     * @param template String containing the content of the template to process
     * @param variables Map containing the key/values for substitution.
     * @return Fully process template with all substitutions
     * @throws OpenCDXNotAcceptable Thrown if variable substitution fails for missing
     * key/value.
     */
    String processHTML(String template, Map<String, Object> variables) throws OpenCDXNotAcceptable;
}
