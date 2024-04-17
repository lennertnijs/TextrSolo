package com.textr.filebuffer;

import com.textr.util.FixedPoint;

public record TextUpdateReference(FixedPoint updateLocation, boolean isInsertion, TextUpdateType type) {
}
