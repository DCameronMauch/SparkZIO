package dcameronmauch.sparkzio.common

import dcameronmauch.sparkzio.resource.config.Config
import dcameronmauch.sparkzio.resource.shellEnv.ShellEnv
import dcameronmauch.sparkzio.resource.spark.Spark
import zio.{ZEnv, ZIO}

object Type {
  type AppZIO = ZIO[Resources, Throwable, Unit]
  type Resources = ZEnv with Config with Spark with ShellEnv
}
