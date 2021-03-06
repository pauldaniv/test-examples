package com.pauldaniv.common.test.groups

final class TestGroup {
  class Slow {
    final class Application extends Slow {
      static final String name = 'application'
    }

    final class Jpa extends Slow {
      static final String name = 'application'
    }

    final class Integration extends Slow {
      static final String name = 'integration'
    }

    static final class Api extends Slow {
      static final String name = 'api'
    }
  }

  class Fast {
    final class Unit extends Fast {
      static final String name = 'unit'
    }

    static final class Component extends Fast {
      static final String name = 'component'
    }
  }
}
