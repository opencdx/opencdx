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
package cdx.opencdx.commons.handlers;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Performance handler for reacording to logs the observability of OpenCDX
 */
@Slf4j
@ExcludeFromJacocoGeneratedReport
public class OpenCDXPerformanceHandler implements ObservationHandler<Observation.Context> {

    /**
     * Default Constructor
     */
    public OpenCDXPerformanceHandler() {
        log.info("Enable Trace logging for OpenCDX Performance logging");
    }

    @Override
    public void onStart(Observation.Context context) {
        log.trace("OpenCDX Execution Started: {}", context.getName());
        context.put("time", System.nanoTime());
        ObservationHandler.super.onStart(context);
    }

    @Override
    public void onError(Observation.Context context) {
        log.trace(
                "OpenCDX Execution Error: {} error {}",
                context.getName(),
                context.getError().getMessage());
        ObservationHandler.super.onError(context);
    }

    @Override
    public void onStop(Observation.Context context) {
        log.trace(
                "OpenCDX Execution Stopped: {} duration {} ms.",
                context.getName(),
                (System.nanoTime() - context.getOrDefault("time", 0L)) / 1000000.0);
        ObservationHandler.super.onStop(context);
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }
}
