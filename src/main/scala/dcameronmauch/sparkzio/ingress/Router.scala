package dcameronmauch.sparkzio.ingress

import dcameronmauch.sparkzio.common.Type.AppZIO
import dcameronmauch.sparkzio.resource.config.Config
import zio.ZIO

object Router {
  def apply(): AppZIO = ZIO.accessM {
    _.get[Config.Service].action.flatMap {
      case "Thing1" => action.Thing1()
      case _ => throw new IllegalArgumentException("action invalid")
    }
  }
}
