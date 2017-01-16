package org.infinispan.remoting.inboundhandler.action;

import java.util.Map;

import org.infinispan.distribution.TriangleOrderManager;
import org.infinispan.util.concurrent.BlockingTaskAwareExecutorService;

/**
 * An {@link Action} that checks if the command is the next to be executed.
 * <p>
 * This action is used by the triangle to order updates from the primary owner to the backup owner.
 *
 * @author Pedro Ruivo
 * @since 9.0
 */
public class TriangleOrderMultiSegmentAction implements Action {

   private final TriangleOrderManager triangleOrderManager;
   private final BlockingTaskAwareExecutorService remoteExecutorService;
   private final Map<Integer, Long> segmentsAndSequences;

   public TriangleOrderMultiSegmentAction(TriangleOrderManager triangleOrderManager,
         BlockingTaskAwareExecutorService remoteExecutorService, Map<Integer, Long> segmentsAndSequences) {
      this.triangleOrderManager = triangleOrderManager;
      this.remoteExecutorService = remoteExecutorService;
      this.segmentsAndSequences = segmentsAndSequences;
   }

   @Override
   public ActionStatus check(ActionState state) {
      return triangleOrderManager.isNext(segmentsAndSequences, state.getCommandTopologyId()) ?
            ActionStatus.READY :
            ActionStatus.NOT_READY;
   }

   @Override
   public void onFinally(ActionState state) {
      segmentsAndSequences.forEach((segmentId, sequenceNumber) -> triangleOrderManager
            .markDelivered(segmentId, sequenceNumber, state.getCommandTopologyId()));
      remoteExecutorService.checkForReadyTasks();
   }
}