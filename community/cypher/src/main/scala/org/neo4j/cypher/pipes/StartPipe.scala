/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.pipes

import org.neo4j.cypher.SymbolTable
import org.neo4j.graphdb.{Relationship, Node, PropertyContainer}
import org.neo4j.cypher.commands.{SymbolType, RelationshipType, NodeType}

class StartPipe[T <: PropertyContainer](name: String, source: Iterable[T]) extends Pipe {
  val symbolType: SymbolType = source match {
    case nodes: Iterable[Node] => NodeType(name)
    case rels: Iterable[Relationship] => RelationshipType(name)
  }

  val symbols: SymbolTable = new SymbolTable(Map(name -> symbolType))

  def foreach[U](f: (Map[String, Any]) => U) {
    source.foreach((x) => {
      f(Map(name -> x))
    })
  }
}