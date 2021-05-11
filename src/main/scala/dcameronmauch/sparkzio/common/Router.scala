package dcameronmauch.sparkzio.common

import dcameronmauch.sparkzio.common.Type.AppZIO
import dcameronmauch.sparkzio.{core, egress, ingress}
import dcameronmauch.sparkzio.resource.config.Config
import zio.ZIO

object Router {
  def apply(): AppZIO = ZIO.accessM {
    _.get[Config.Service].section.flatMap {
      case "ingress" => ingress.Router()
      case "core" => core.Router()
      case "egress" => egress.Router()
      case _ => throw new IllegalArgumentException("section invalid")
    }
  }
}
