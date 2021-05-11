package dcameronmauch.sparkzio.resource

import zio.{Has, Task, ULayer, ZLayer}

object config {
  type Config = Has[Config.Service]

  object Config {
    trait Service {
      val section: Task[String]
      val action: Task[String]
      val reference: Task[String]
      val snapshot: Task[String]
    }

    def live(args: List[String]): Service = new Service {
      val section: Task[String] = get(0, "section unspecified")
      val action: Task[String] = get(1, "action unspecified")
      val reference: Task[String] = get(2, "reference unspecified")
      val snapshot: Task[String] = get(3, "snapshot unspecified")

      def get(index: Int, error: String): Task[String] = Task {
        args.drop(index).headOption.getOrElse(throw new IllegalArgumentException(error))
      }
    }
  }

  def live(args: List[String]): ULayer[Config] = ZLayer.succeed(Config.live(args))
}