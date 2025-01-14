package org.infinispan.globalstate.impl;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.configuration.ConfigurationManager;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.globalstate.LocalConfigurationStorage;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.util.logging.Log;
import org.infinispan.util.logging.LogFactory;

/**
 * An immutable implementation of {@link LocalConfigurationStorage} which does not allow cache creation/removal.
 *
 * @author Tristan Tarrant
 * @since 9.2
 */
public class ImmutableLocalConfigurationStorage implements LocalConfigurationStorage {
   protected static Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

   @Override
   public void initialize(EmbeddedCacheManager embeddedCacheManager, ConfigurationManager configurationManager, Executor executor) {
   }

   @Override
   public void validateFlags(EnumSet<CacheContainerAdmin.AdminFlag> flags) {
      throw log.immutableConfiguration();
   }

   @Override
   public CompletableFuture<Void> createCache(String name, String template, Configuration configuration, EnumSet<CacheContainerAdmin.AdminFlag> flags) {
      throw log.immutableConfiguration();
   }

   @Override
   public CompletableFuture<Void> removeCache(String name, EnumSet<CacheContainerAdmin.AdminFlag> flags) {
      throw log.immutableConfiguration();
   }

   @Override
   public Map<String, Configuration> loadAll() {
      return Collections.emptyMap();
   }
}
