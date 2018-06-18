/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FrTestModule } from '../../../test.module';
import { AnomaliaMedidorMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/anomalia-medidor-my-suffix/anomalia-medidor-my-suffix-delete-dialog.component';
import { AnomaliaMedidorMySuffixService } from '../../../../../../main/webapp/app/entities/anomalia-medidor-my-suffix/anomalia-medidor-my-suffix.service';

describe('Component Tests', () => {

    describe('AnomaliaMedidorMySuffix Management Delete Component', () => {
        let comp: AnomaliaMedidorMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<AnomaliaMedidorMySuffixDeleteDialogComponent>;
        let service: AnomaliaMedidorMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [AnomaliaMedidorMySuffixDeleteDialogComponent],
                providers: [
                    AnomaliaMedidorMySuffixService
                ]
            })
            .overrideTemplate(AnomaliaMedidorMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnomaliaMedidorMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnomaliaMedidorMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
