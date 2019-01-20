package ht.eyfout.map.data.storage.visitor;

import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.data.storage.ScalarDataStorage;

public interface DataStorageVisitor {

  void pre(GroupDataStorage storage);
  void post(GroupDataStorage storage);

  VisitorResult visit(String id, ScalarDataStorage storage);
  <T> T result();
}
