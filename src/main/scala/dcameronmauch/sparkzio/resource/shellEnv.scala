package dcameronmauch.sparkzio.resource

import zio.{Has, UIO, ULayer, ZLayer}

object shellEnv {
  type ShellEnv = Has[ShellEnv.Service]

  object ShellEnv {
    trait Service {
      val dbHost: UIO[String]
      val dbLogin: UIO[String]
      val dbPassword: UIO[String]
    }

    val live: Service = new Service {
      val dbHost: UIO[String] = UIO { sys.env.getOrElse("DB_HOST", "localhost") }
      val dbLogin: UIO[String] = UIO { sys.env.getOrElse("DB_LOGIN", "user") }
      val dbPassword: UIO[String] = UIO { sys.env.getOrElse("DB_PASSWORD", "secret") }
    }
  }

   val live: ULayer[ShellEnv] = ZLayer.succeed(ShellEnv.live)
}
