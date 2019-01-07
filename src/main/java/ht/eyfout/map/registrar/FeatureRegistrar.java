package ht.eyfout.map.registrar;

import com.google.common.collect.Lists;
import ht.eyfout.map.factory.FeatureFactory;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.features.FeatureProfile;
import ht.eyfout.map.features.GroupFeature;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;

public final class FeatureRegistrar {
  final Map<Integer, FeatureBundle> bundles = new ConcurrentHashMap<>();
  private final FeatureBundle FEATURELESS_BUNDLE = new FeatureBundle();
  List<FeatureFactory> featureFactories;
  private int FEATURELESS_BUNDLEID = 1 << 30;

  @Inject
  public FeatureRegistrar(Set<FeatureFactory> featureFactories) {
    this.featureFactories = Lists.newArrayList(featureFactories);
    this.featureFactories.sort(
        (a, b) ->
            Integer.signum(a.profile().descriptor().rank() - b.profile().descriptor().rank()));
  }

  public FeatureBundle bundle(FeatureDescriptor... descriptors) {
    int bundleId = bundleId(descriptors);
    FeatureBundle bundle = bundles.get(bundleId);
    if (null == bundle) {
      bundle = new FeatureBundle(descriptors);
    }
    return bundle;
  }

  public GroupFeature pageFeature(FeatureDescriptor descriptor) {
    return get(descriptor, FeatureFactory::pageFeature);
  }

  protected <R> R get(FeatureDescriptor descriptor, Function<FeatureFactory, R> func) {
    return func.apply(get(descriptor));
  }

  protected FeatureFactory get(FeatureDescriptor descriptor) {
    return featureFactories
        .parallelStream()
        .filter((factory) -> descriptor == factory.profile().descriptor())
        .findFirst()
        .orElseThrow(IllegalStateException::new);
  }

  protected int bundleId(FeatureDescriptor... descriptors) {
    if (0 == descriptors.length) {
      return FEATURELESS_BUNDLEID;
    }
    return Arrays.stream(descriptors)
        .map(FeatureDescriptor::rank)
        .reduce((a, b) -> a | b)
        .orElseThrow(IllegalStateException::new);
  }

  public class FeatureBundle {
    int id;
    Set<FeatureFactory> bundledFactories;
    //    private boolean delegateTo;
    private FeatureBundle delegate;
    private FeatureChain chainedFeatures;

    protected FeatureBundle() {
      id = bundleId();
      bundledFactories = Collections.EMPTY_SET;
      bundles.put(id, this);
    }

    protected FeatureBundle(FeatureDescriptor... descriptors) {
      this(Arrays.stream(descriptors).map(FeatureRegistrar.this::get).collect(Collectors.toSet()));
    }

    protected FeatureBundle(Set<FeatureFactory> factories) {
      bundledFactories = factories;
      this.id =
          bundleId(
              bundledFactories
                  .stream()
                  .map(FeatureFactory::profile)
                  .map(FeatureProfile::descriptor)
                  .toArray(FeatureDescriptor[]::new));
      bundles.put(id, this);

      FeatureDescriptor[] dependsOn =
          bundledFactories
              .stream()
              .map(FeatureFactory::profile)
              .map(FeatureProfile::descriptor)
              .map(FeatureDescriptor::dependsOn)
              .reduce(
                  (a, b) -> Stream.of(a, b).flatMap(Stream::of).toArray(FeatureDescriptor[]::new))
              .orElse(new FeatureDescriptor[0]);

      dependsOn =
          Arrays.stream(dependsOn)
              .filter((desc) -> !bundledFactories.contains(FeatureRegistrar.this.get(desc)))
              .toArray(FeatureDescriptor[]::new);

      System.out.println(toString() + bundles);

      if (0 < dependsOn.length) {
        delegate = join(bundle(dependsOn));
        System.out.println(this + " delegating to " + delegate);
        bundledFactories = delegate.bundledFactories;
        chainedFeatures = delegate.chainedFeatures;
      } else {
        chainedFeatures = new FeatureChain(bundledFactories);
      }
    }

    public <R> R get(FeatureDescriptor descriptor, Function<FeatureFactory, R> func) {
      return FeatureRegistrar.this.get(descriptor, func);
    }

    public <R> R chain(Function<FeatureFactory, R> func) {
      return func.apply(chainedFeatures);
    }

    @Override
    public String toString() {
      return "id:"
          + id
          + ", features ["
          + bundledFactories
              .stream()
              .map(FeatureFactory::profile)
              .map(FeatureProfile::descriptor)
              .map(FeatureDescriptor::name)
              .reduce((a, b) -> a + ", " + b)
              .orElse("")
          + " ]";
    }

    public FeatureBundle withFeature(FeatureDescriptor... descriptor) {
      int bundleId = this.id | bundleId(descriptor);
      FeatureBundle bundle = bundles.get(bundleId);
      if (null == bundle) {
        return this.join(bundle(descriptor));
      }
      return bundle;
    }

    public FeatureBundle withoutFeature(FeatureDescriptor... descriptors) {
      Set<FeatureDescriptor> setOfDescriptors =
          Arrays.stream(descriptors).collect(Collectors.toSet());
      return new FeatureBundle(
          bundledFactories
              .stream()
              .map(FeatureFactory::profile)
              .map(FeatureProfile::descriptor)
              .filter((descriptor) -> !setOfDescriptors.contains(descriptor))
              .toArray(FeatureDescriptor[]::new));
    }

    private FeatureBundle join(FeatureBundle bundle) {
      Set<FeatureFactory> factories =
          Stream.concat(bundle.bundledFactories.stream(), bundledFactories.stream())
              .collect(Collectors.toSet());

      return bundle(
          factories
              .parallelStream()
              .map(FeatureFactory::profile)
              .map(FeatureProfile::descriptor)
              .toArray(FeatureDescriptor[]::new));
    }
  }
}
