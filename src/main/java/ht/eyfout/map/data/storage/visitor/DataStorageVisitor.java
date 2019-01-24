package ht.eyfout.map.data.storage.visitor;

import ht.eyfout.map.data.storage.GroupDataStorage;

public interface DataStorageVisitor {

  void pre(GroupDataStorage storage);

  void post(GroupDataStorage storage);

  <T> VisitorResult visit(String id, T storage);

  <T> T result();
}
