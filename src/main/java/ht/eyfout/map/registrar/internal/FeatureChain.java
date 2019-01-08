package ht.eyfout.map.registrar.internal;

import ht.eyfout.map.factory.FeatureFactory;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.feature.FeatureProfile;
import ht.eyfout.map.feature.GroupFeature;
import ht.eyfout.map.feature.ScalarFeature;
import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class FeatureChain extends FeatureFactory {

  private GroupFeature groupFeature;
  private ScalarFeature scalarFeature;
  private BinaryOperator<FeatureFactory> mappingFunc =
      new BinaryOperator<FeatureFactory>() {
        @Override
        public FeatureFactory apply(FeatureFactory f1, FeatureFactory f2) {
          groupFeature = f2.groupFeature(f1.groupFeature());
          scalarFeature = f2.scalarFeature(f1.scalarFeature());
          return f1;
        }
      };

  FeatureChain(Collection<FeatureFactory> factories) {
    Stream.concat(factories.stream(), Stream.of(this))
        .sorted((rhs, lhs) -> Integer.signum(lhs.profile().order() - rhs.profile().order()))
        .reduce(mappingFunc);
  }

  @Override
  public GroupFeature groupFeature(GroupFeature groupFeature) {
    return this.groupFeature;
  }

  @Override
  public ScalarFeature scalarFeature(ScalarFeature scalarFeature) {
    return this.scalarFeature;
  }

  @Override
  public FeatureProfile profile() {
    return FeatureProfile.create(new FeatureChainDescriptor());
  }

  final class FeatureChainDescriptor implements FeatureDescriptor {

    @Override
    public String name() {
      return FeatureChain.class.getName();
    }

    @Override
    public FeatureDescriptor[] dependsOn() {
      return new FeatureDescriptor[0];
    }

    @Override
    public int ordinal() {
      return -1;
    }

    @Override
    public FeatureState state() {
      return FeatureState.STATELESS;
    }
  }
}
