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
package cdx.opencdx.commons.service;


/**
 * OpenCDXHtmlSanitizer interface offers capabilities to cleanse HTML content.
 * Ideally, it is employed in scenarios where client-provided HTML requires
 * sanitization to thwart cross-site scripting (XSS) assaults.
 */
public interface OpenCDXHtmlSanitizer {

    /**
     * Sanitize the HTML string.
     * @param html HTML string to be sanitized.
     * @return String comprising the cleansed HTML.
     */
    String sanitize(String html);

}
