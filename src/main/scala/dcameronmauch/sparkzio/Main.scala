package dcameronmauch.sparkzio

import dcameronmauch.sparkzio.common.Router
import dcameronmauch.sparkzio.resource.{config, shellEnv, spark}
import zio.{App, ExitCode, URIO, ZEnv}

object Main extends App {
  def run(args: List[String]): URIO[ZEnv, ExitCode] =
    Router()
      .provideCustomLayer(config.live(args) ++ shellEnv.live ++ spark.live)
      .exitCode
 }
