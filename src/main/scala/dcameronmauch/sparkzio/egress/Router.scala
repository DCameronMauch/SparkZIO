package dcameronmauch.sparkzio.egress

import dcameronmauch.sparkzio.common.Type.AppZIO
import dcameronmauch.sparkzio.resource.config.Config
import zio.ZIO

object Router {
  def apply(): AppZIO = ZIO.accessM {
    _.get[Config.Service].action.flatMap {
      case "Thing3" =>
        println("egress thing 3")
        ZIO.succeed(())
      case _ => throw new IllegalArgumentException("action invalid")
    }
  }
}
