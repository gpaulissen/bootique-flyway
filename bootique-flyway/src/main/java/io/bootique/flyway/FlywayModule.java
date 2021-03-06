/*
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.bootique.flyway;

import io.bootique.BQCoreModule;
import io.bootique.ConfigModule;
import io.bootique.config.ConfigurationFactory;
import io.bootique.di.Binder;
import io.bootique.di.Provides;
import io.bootique.flyway.command.BaselineCommand;
import io.bootique.flyway.command.CleanCommand;
import io.bootique.flyway.command.InfoCommand;
import io.bootique.flyway.command.MigrateCommand;
import io.bootique.flyway.command.RepairCommand;
import io.bootique.flyway.command.ValidateCommand;
import io.bootique.jdbc.DataSourceFactory;

import static java.util.Arrays.asList;

public class FlywayModule extends ConfigModule {

    @Override
    public void configure(Binder binder) {
        asList(
                BaselineCommand.class,
                CleanCommand.class,
                InfoCommand.class,
                MigrateCommand.class,
                RepairCommand.class,
                ValidateCommand.class
        ).forEach(command -> BQCoreModule.extend(binder).addCommand(command));
    }

    @Provides
    public FlywaySettings createFlywayDataSources(ConfigurationFactory configFactory, DataSourceFactory dataSourceFactory) {
        return config(FlywayFactory.class, configFactory).createDataSources(dataSourceFactory);
    }

    @Provides
    public FlywayRunner createFlywayRunner(FlywaySettings flywaySettings) {
        return new FlywayRunner(flywaySettings);
    }
}
