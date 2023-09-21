/*
 * Copyright 2023 Safe Health Systems, Inc.
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
package health.safe.api.opencdx.commons.handlers;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpenCDXPerformanceHandler implements ObservationHandler<Observation.Context> {
    @Override
    public void onStart(Observation.Context context) {
        log.info("OpenCDX Execution Started: {}", context.getName());
        context.put("time", System.nanoTime());
        ObservationHandler.super.onStart(context);
    }

    @Override
    public void onError(Observation.Context context) {
        log.info(
                "OpenCDX Execution Error: {} error {}",
                context.getName(),
                context.getError().getMessage());
        ObservationHandler.super.onError(context);
    }

    @Override
    public void onEvent(Observation.Event event, Observation.Context context) {
        ObservationHandler.super.onEvent(event, context);
    }

    @Override
    public void onScopeOpened(Observation.Context context) {
        ObservationHandler.super.onScopeOpened(context);
    }

    @Override
    public void onScopeClosed(Observation.Context context) {
        ObservationHandler.super.onScopeClosed(context);
    }

    @Override
    public void onScopeReset(Observation.Context context) {
        ObservationHandler.super.onScopeReset(context);
    }

    @Override
    public void onStop(Observation.Context context) {
        log.info(
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
