package controllers

import java.io.File
import java.nio.file.Paths

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.{Configuration, Logger}
import play.api.mvc.{AbstractController, ControllerComponents}
import services.ImageService

import scala.concurrent.ExecutionContext



class PixController  @Inject()(cc: ControllerComponents,
                               config: Configuration,
                               img: ImageService
                              )
                              (implicit ec: ExecutionContext) extends AbstractController(cc) {

  val log = Logger(this.getClass)
  val dirPath = config.get[String]("data.dir")

  def upload = Action(parse.multipartFormData) { request =>
    request.body
      .file("image")
      .map { picture =>
        val filename    = Paths.get(picture.filename).getFileName
        picture.ref.copyTo(Paths.get(s"$dirPath/$filename"), replace = true)
        img.convert(filename.toString)
        Ok("File Uploaded")
      }
      .getOrElse {
        Ok("Missing file")
      }
  }

  def retrieveOriginal(file: String) = Action { request =>
    Ok.sendFile(new File(s"/Users/Malay/test/upload/$file"))
  }

  def retrieveConverted(file: String) = Action { request =>
    Ok.sendFile(new File(s"/Users/Malay/test/upload/converted-$file"))
  }

  def list() = Action { Ok(Json.toJson(img.listFiles)) }

}
