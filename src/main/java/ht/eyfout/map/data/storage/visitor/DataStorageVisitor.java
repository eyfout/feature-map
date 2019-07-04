package ht.eyfout.map.data.storage.visitor;

import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.data.storage.ScalarDataStorage;
import ht.eyfout.map.visitor.VisitorResult;

public interface DataStorageVisitor<R> {

  void pre(GroupDataStorage storage);

  R post(GroupDataStorage storage);

  VisitorResult visit(String id, ScalarDataStorage storage);
}
