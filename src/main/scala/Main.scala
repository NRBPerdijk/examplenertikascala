import StringsToAnalyse._
import ner.NERResults
import ner.NERTypes._
import org.apache.tika.parser.ner.opennlp.OpenNLPNERecogniser

object Main extends App {

  // First, you gather the models you require and give them a recognisable label for the results.
  val models = new java.util.HashMap[String, String]()
  models.put(NERDate.serialized, "en-ner-date.bin")
  models.put(NERLocations.serialized, "en-ner-location.bin")
  models.put(NEROrganisations.serialized, "en-ner-organization.bin")
  models.put(NERPersons.serialized, "en-ner-person.bin")

  // Then you can feed your models to the OpenNLPNERecogniser, so it known to use these
  val nerRecogniser = new OpenNLPNERecogniser(models)

  // Then you can feed texts you'd like to analyse to the nerRecogniser
  val resultsPart1 = NERResults(nerRecogniser.recognise(articleTextPart1))
  val resultsPart2 = NERResults(nerRecogniser.recognise(articleTextPart2))
  val resultsPart3 = NERResults(nerRecogniser.recognise(articleTextPart3))
  val resultsPart4 = NERResults(nerRecogniser.recognise(articleTextPart4))
  val resultsPart5 = NERResults(nerRecogniser.recognise(articleTextPart5))

  // Let's see some results!
  Set(resultsPart1, resultsPart2, resultsPart3, resultsPart4, resultsPart5).foreach(_.prettyPrint())
}
