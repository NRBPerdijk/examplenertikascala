package ner

import java.util

import ner.NERTypes._

import scala.jdk.CollectionConverters._

case class NERResults(setOfNERResults: Set[NERResult]) {
  def prettyPrint(): Unit = {
    println(">>>>> Printing NER Result <<<<<")
    setOfNERResults.foreach(_.prettyPrint())
    println()
  }
}

/**
  * Convenience class for extracting results from the Map that Tika uses.
  */
object NERResults {
  def apply(tikaNerRecogniserResult: util.Map[String, util.Set[String]]): NERResults =
    NERResults(
        tikaNerRecogniserResult.asScala.toMap.foldLeft(Set[NERResult]()){
          (acc, kv) => NERTypes.deserialized(kv._1) match {
            case NERLocations => acc + LocationsNERResult(kv._2.asScala.toSet)
            case NEROrganisations => acc + OrganisationsNERResult(kv._2.asScala.toSet)
            case NERPersons => acc + PersonsNERResult(kv._2.asScala.toSet)
            case NERDate => acc + DateNERResult(kv._2.asScala.toSet)
            case Unknown =>
              println(s"WARNING: A weird group of results has been found with key ${kv._1}")
              acc
        }
      }
    )
}

trait NERResult {
  val nerType: NERType
  val waardes: Set[String]
  def prettyPrint(): Unit = println(s"${nerType.serialized}: $waardes")
}

case class LocationsNERResult(waardes: Set[String]) extends NERResult {
  override val nerType: NERType = NERLocations
}
case class OrganisationsNERResult(waardes: Set[String]) extends NERResult {
  override val nerType: NERType = NEROrganisations
}
case class PersonsNERResult(waardes: Set[String]) extends NERResult {
  override val nerType: NERType = NERPersons
}
case class DateNERResult(waardes: Set[String]) extends NERResult {
  override val nerType: NERType = NERDate
}
